// Copyright 2020 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.bazel.repository;

import static com.google.common.truth.Truth.assertThat;
import static com.google.devtools.build.lib.bazel.repository.TestArchiveDescriptor.INNER_FOLDER_NAME;
import static com.google.devtools.build.lib.bazel.repository.TestArchiveDescriptor.ROOT_FOLDER_NAME;

import com.google.devtools.build.lib.vfs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests Zstandard (zstd) decompression. */
@RunWith(JUnit4.class)
public class TarZstdFunctionTest {
  private static final String ARCHIVE_NAME = "test_decompress_archive.tar.zst";

  /**
   * Test decompressing a zstd file.
   */
  @Test
  public void testZstdDecompress() throws Exception {
    TestArchiveDescriptor archiveDescriptor = new TestArchiveDescriptor(ARCHIVE_NAME, "out", false);

    Path outputDir = decompress(archiveDescriptor.createDescriptorBuilder());

    archiveDescriptor.assertOutputFiles(outputDir, INNER_FOLDER_NAME);
  }

  private Path decompress(DecompressorDescriptor.Builder descriptorBuilder) throws Exception {
    descriptorBuilder.setDecompressor(TarZstdFunction.INSTANCE);
    return TarZstdFunction.INSTANCE.decompress(descriptorBuilder.build());
  }
}
